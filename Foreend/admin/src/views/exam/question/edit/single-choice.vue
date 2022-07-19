<template>
  <div class="app-container">
    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="专业分类："  required>
        <el-select v-model="form.direction.profession.professionName" placeholder="专业分类"  @change="levelChange" clearable>
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="专业方向：" prop="direction" required>
        <el-input v-model="form.direction.directionName"  class="question-item-content-input" />
      </el-form-item>
      <el-form-item label="题干：" prop="body" required >
        <el-input v-model="form.body"  class="question-item-content-input"/>
      </el-form-item>
      <el-form-item label="选项：" required>
        <el-form-item :label="item.prefix" :key="item.prefix"  v-for="(item,index) in form.selection"  label-width="50px" class="question-item-label">
          <el-input v-model="item.content"   class="question-item-content-input"/>
           <el-button type="danger" size="mini" class="question-item-remove" icon="el-icon-delete" @click="questionItemRemove(index)"></el-button>
        </el-form-item>
      </el-form-item>
      <el-form-item label="正确答案：" prop="answer" required>
        <el-input v-model="form.answer" class="question-item-content-input"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
        <el-button type="success" @click="questionItemAdd">添加选项</el-button>
      </el-form-item>
    </el-form>
    <el-dialog  :visible.sync="richEditor.dialogVisible"  append-to-body :close-on-click-modal="false" style="width: 100%;height: 100%"   :show-close="false" center>
      <Ueditor @ready="editorReady"/>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editorConfirm">确 定</el-button>
        <el-button @click="richEditor.dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="questionShow.dialog" style="width: 100%;height: 100%">
      <QuestionShow :qType="questionShow.qType" :question="questionShow.question" :qLoading="questionShow.loading"/>
    </el-dialog>
  </div>
</template>

<script>
/* eslint-disable*/
import QuestionShow from '../components/Show'
import Ueditor from '@/components/Ueditor'
import { mapGetters, mapState, mapActions } from 'vuex'
import questionApi from '@/api/question'

const form =
  {
    direction:{
      directionID: null,
      directionName:'',
      profession:{
        professionID: '',
        professionName:'',
      }
    },
    questionID: 1,
    type: 1,
    body: '',
    selection: [
      { prefix: 'A', content: '' },
      { prefix: 'B', content: '' },
      { prefix: 'C', content: '' },
      { prefix: 'D', content: '' }
    ],
    answer: '',
  }

export default {
  components: {
    Ueditor, QuestionShow
  },
  data () {
    return {
      form: {
        questionID: null,  // id
        type: 1,  // 题目类型
        direction:{
          directionID: null,
          directionName:'',
          profession:{
            professionID: '',
            professionName:'',
          }
        },
        body: '', // 题目题干
        selection: [
          { prefix: 'A', content: '' },
          { prefix: 'B', content: '' },
          { prefix: 'C', content: '' },
          { prefix: 'D', content: '' }
        ],
        answer: '' // 答案
      },
      subjectFilter: null, // 专业方向
      formLoading: false,
      rules: {
        profession: [
          { required: true, message: '请选择专业分类', trigger: 'change' }
        ],
        direction: [
          { required: true, message: '请选择专业方向', trigger: 'change' }
        ],
        title: [
          { required: true, message: '请输入题干', trigger: 'blur' }
        ],
        answer: [
          { required: true, message: '请选择正确答案', trigger: 'change' }
        ]
      },
      richEditor: {
        dialogVisible: false,
        object: null,
        parameterName: '',
        instance: null
      },
      questionShow: {
        qType: 0,
        dialog: false,
        question: null,
        loading: false
      }
    }
  },
  created () {
    let id = this.$route.query.id
    let _this = this
    this.initSubject(function () {
      _this.subjectFilter = _this.subjects
    })
    if (id && parseInt(id) !== 0) {
      _this.formLoading = true
      questionApi.select(id).then(re => {
        _this.form = re.data
        _this.formLoading = false
      })
    }
  },
  methods: {
    editorReady (instance) {
      this.richEditor.instance = instance
      let currentContent = this.richEditor.object[this.richEditor.parameterName]
      this.richEditor.instance.setContent(currentContent)
      // 光标定位到Ueditor
      this.richEditor.instance.focus(true)
    },
    inputClick (object, parameterName) {
      this.richEditor.object = object
      this.richEditor.parameterName = parameterName
      this.richEditor.dialogVisible = true
    },
    editorConfirm () {
      let content = this.richEditor.instance.getContent()
      this.richEditor.object[this.richEditor.parameterName] = content
      this.richEditor.dialogVisible = false
    },
    questionItemRemove (index) {
      this.form.selection.splice(index, 1)
    },
    questionItemAdd () {
      let selection = this.form.selection
      let newLastPrefix
      if (selection.length > 0) {
        let last = selection[selection.length - 1]
        newLastPrefix = String.fromCharCode(last.prefix.charCodeAt() + 1)
      } else {
        newLastPrefix = 'A'
      }
      selection.push({ id: null, prefix: newLastPrefix, content: '' })
    },
    submitForm () {
      let _this = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true
          // 根据方向名称查找方向id
          questionApi.getDirectionIDByNAME(this.form.direction.directionName,
            this.form.direction.profession.professionID).then(re=>{
            if(re.code !==200) {
              alert('专业方向不存在！')
              _this.$message.error(re.message)
            }
            else{
              this.form.direction.directionID=re.data
            }
          }).catch(e => {
            this.formLoading = false
          })
          // 根据专业名称查找专业id
          let levelEnum = [{ key: 1, value: '毛概' }, { key: 2, value: '马原' }, { key: 3, value: '军理' }, { key: 4, value: '思修' }]
          for(let i in levelEnum){
            if(levelEnum[i].key=== parseInt(this.form.direction.profession.professionID)){
              this.form.direction.profession.professionName=levelEnum[i].value
            }
          }
          // 如果没有id，则选择添加题目
          if(!this.form.questionID || this.form.questionID===null){
            questionApi.addOne(this.form).then(re => {
              if (re.code === 200) {
                _this.$message.success(re.message)
                _this.delCurrentView(_this).then(() => {
                  _this.$router.push('/exam/question/list')
                })
              } else {
                _this.$message.error(re.message)
                this.formLoading = false
              }
            }).catch(e => {
              this.formLoading = false
            })
          }
          // 如果有id，则编辑题目
          else {
            questionApi.editQuestion(this.form).then(re => {
              if (re.code === 200) {
                _this.$message.success(re.message)
                _this.delCurrentView(_this).then(() => {
                  _this.$router.push('/exam/question/list')
                })
              } else {
                _this.$message.error(re.message)
                this.formLoading = false
              }
            }).catch(e => {
              this.formLoading = false
            })
          }
        } else {
          return false
        }
      })
    },
    resetForm () {
      let lastId = this.form.questionID
      this.$refs['form'].resetFields()
      this.form = {
        questionID: null,  // id
        type: 1,  // 题目类型
        direction:{
          directionID: null,
          directionName:'',
          profession:{
            professionID: '',
            professionName:'',
          }
        },
        body: '', // 题目题干
        selection: [
          { prefix: 'A', content: '' },
          { prefix: 'B', content: '' },
          { prefix: 'C', content: '' },
          { prefix: 'D', content: '' }
        ],
        answer: '' // 答案
      }
      this.form.questionID = lastId
    },
    levelChange () {
      this.form.direction = null
      this.subjectFilter = this.subjects.filter(data => data.profession === this.form.profession)
    },
    showQuestion () {
      this.questionShow.dialog = true
      this.questionShow.qType = this.form.type
      this.questionShow.question = this.form
    },
    ...mapActions('exam', { initSubject: 'initSubject' })
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      typeEnum: state => state.exam.question.typeEnum,
      levelEnum: state => state.exam.question.levelEnum
    }),
    ...mapState('exam', { subjects: state => state.subjects })
  }
}
</script>
