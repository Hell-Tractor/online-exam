<template>
  <div class="app-container">
    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="专业分类：" required>
        <el-select v-model="form.direction.profession.professionID"   placeholder="专业分类"  >
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="专业方向：" prop="direction.directionName" required>
        <el-input v-model="form.direction.directionName" placeholder="请输入专业方向" />
      </el-form-item>
      <el-form-item label="题干：" prop="body" required>
        <el-input v-model="form.body" placeholder="请输入题干"/>
      </el-form-item>
      <el-form-item label="答案：" prop="answer" required>
        <el-input v-model="form.answer"  placeholder="请输入答案" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="firstSubmit">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
        <el-button  type="danger" @click="deleteFormConfirm">清空</el-button>
      </el-form-item>
    </el-form>
    <el-dialog title="提交" :visible.sync="dialogVisible" width="30%">
      <span>确定提交吗？</span>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="清空" :visible.sync="deleteVisible" width="30%">
      <span>确定清空吗？</span>
      <span slot="footer" class="dialog-footer">
      <el-button @click="deleteVisible = false">取消</el-button>
      <el-button type="primary" @click="deleteForm">确定</el-button>
      </span>
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
      type: 4,
      body: '',
      selection: [],
      answer: 'o',
}

export default {
  components: {
    Ueditor, QuestionShow
  },

  // data {} 中没有定义一级对象 (items)中的第二级对象 (deviceVO),所以会报 undefined 的错误
  data () {
    return {
      form: {
        direction:{
          directionID: null,
          directionName:'',
          profession:{
            professionID: null,
            professionName:'',
          }
        },
        questionID: null,
        type: 4,
        body: '',
        selection: [],
        answer: '',
      },
      returnSearch:{
        professionID:'',
        directionName:''
      },
      subjectFilter: null,
      formLoading: false,
      rules: {
        profession: [
          { required: true, message: '请选择专业分类', trigger: 'change' }
        ],
        title: [
          { required: true, message: '请输入题干', trigger: 'blur' }
        ],
        answer: [
          { required: true, message: '请输入答案', trigger: 'blur' }
        ],
      },
      dialogVisible:false,
      deleteVisible:false
    }
  },
  activated(){
    if(this.$route.query.id){
      let id = this.$route.query.id
      this.formLoading = true
      questionApi.select(id).then(re => {
        this.form = re.data
        this.formLoading = false
      })
    }
    else{
      this.deleteForm()
    }
  },
  created () {
    let _this = this
    this.initSubject(function () {
      _this.subjectFilter = _this.subjects
    })
    _this.formLoading = false
  },
  methods: {
    deleteFormConfirm(){
      this.deleteVisible=true
    },
    firstSubmit(){
      this.dialogVisible=true
      let _this = this
      // 根据方向名称查找方向id
      this.returnSearch.professionID=this.form.direction.profession.professionID.toString()
      this.returnSearch.directionName=this.form.direction.directionName
      questionApi.getDirectionIDByNAME(this.returnSearch).then(re => {
        if (re.data) {
          this.form.direction.directionID = re.data
        }
      }).catch(e => {
        if(e=='directionID not found'){
          this.$message.error('专业方向不存在，请先添加！')
          _this.$router.push('/education/subject/list')
        }
        this.formLoading = false
      })
      // 根据方向名称查找方向id end
    },
    submitForm () {
      this.dialogVisible = false
      this.$refs.form.validate((valid) => {
        if (valid) {
          if(this.form.direction.directionID){
            this.formLoading = true
            // 根据专业名称查找专业id
            let levelEnum = [{key: 1, value: '毛概'}, {key: 2, value: '马原'}, {key: 3, value: '军理'}, {key: 4, value: '思修'}]
            for (let i in levelEnum) {
              if (levelEnum[i].key === parseInt(this.form.direction.profession.professionID)) {
                this.form.direction.profession.professionName = levelEnum[i].value
              }
            }
            // 如果没有id，则选择添加题目
            if (!this.form.questionID || this.form.questionID === null) {
              questionApi.addOne(this.form).then(re => {
                if (re.code === 200) {
                  this.$message.success('添加成功！')
                  this.delCurrentView(this).then(() => {
                    this.$router.push('/exam/question/list')
                  })
                } else {
                  this.$message.error(re.message)
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
                  this.$message.success('编辑成功！')
                  this.delCurrentView(this).then(() => {
                    this.$router.push('/exam/question/list')
                  })
                } else {
                  this.formLoading = false
                }
              }).catch(e => {
                if(e=='directionID not found'){
                  this.$message.error('专业方向不存在，请先添加！')
                  this.$router.push('/education/subject/list')
                }
                this.formLoading = false
              })
            }
          }
          else{
            this.$message.error('专业方向不正确！')
            return false
          }
        }
        else {
          return false
        }
      })
    },
    //重置
    resetForm(){
      location.reload()
    },
    //清空
    deleteForm () {
      let lastId = this.form.questionID
      this.$refs['form'].resetFields()
      this.form = {
        direction:{
          directionID: null,
            directionName:'',
            profession:{
            professionID: '',
              professionName:'',
          }
        },
        questionID: 1,
          type: 4,
        body: '',
        selection: [],
        answer: '',
      }
      this.form.questionID = lastId
      this.dialogVisible=false
      this.deleteVisible=false
    },
    ...mapActions('exam', { initSubject: 'initSubject' }),
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
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
