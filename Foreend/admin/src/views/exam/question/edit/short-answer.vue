<template>
  <div class="app-container">
    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="专业分类：" prop="profession" required>
        <el-select v-model="form.profession"   placeholder="专业分类"  @change="levelChange">
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="专业方向：" prop="direction" required>
<!--        专业方向测试-->
<!--        <el-select v-model="form.direction" placeholder="专业方向" >-->
<!--          <el-option v-for="item in subjectFilter" :key="item.id" :value="item.id" :label="item.name"></el-option>-->
<!--        </el-select>-->
        <el-input v-model="form.direction" />
      </el-form-item>
      <el-form-item label="题干：" prop="body" required>
        <el-input v-model="form.body"   @focus="inputClick(form,'title')" />
      </el-form-item>
      <el-form-item label="答案：" prop="answer" required>
        <el-input v-model="form.answer"   @focus="inputClick(form,'answer')" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
        <el-button type="success" @click="showQuestion">预览</el-button>
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
import QuestionShow from '../components/Show'
import Ueditor from '@/components/Ueditor'
import { mapGetters, mapState, mapActions } from 'vuex'
import questionApi from '@/api/question'

export default {
  components: {
    Ueditor, QuestionShow
  },
  data () {
    return {
      form: {
        id: null,
        type: 4,
        profession: null,
        direction: null,
        body: '',
        selection: [],
        answer: '',
      },
      subjectFilter: null,
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
          { required: true, message: '请输入答案', trigger: 'blur' }
        ],
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
        _this.form = re.response
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
    submitForm () {
      let _this = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.formLoading = true
          questionApi.edit(this.form).then(re => {
            if (re.code === 1) {
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
        } else {
          return false
        }
      })
    },
    resetForm () {
      let lastId = this.form.id
      this.$refs['form'].resetFields()
      this.form = {
        id: null,
        type: 4,
        profession: null,
        direction: null,
        body: '',
        selection: [],
        answer: '',
      }
      this.form.id = lastId
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
