<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading">
      <el-form-item label="专业方向：" required>
        <el-input v-model="form.directionName"></el-input>
      </el-form-item>
      <el-form-item label="专业分类：" required>
        <el-select v-model="form.profession.professionID" placeholder="专业分类">
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
        <el-button  type="danger" @click="deleteFormConfirm">清空</el-button>
      </el-form-item>
    </el-form>
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
import { mapGetters, mapState, mapActions } from 'vuex'
import subjectApi from '@/api/subject'

const form={
  directionID:1,
  directionName:'',
  profession:{
    professionID: 1, // 分类id
    professionName: '' // 分类名称
  }
}

export default {
  data () {
    return {
      form: {
        directionID: null, // 专业方向id
        directionName: '', // 专业方向名称
        profession:{
          professionID: 1, // 分类id
          professionName: '' // 分类名称
        }
      },
      formLoading: false,
      deleteVisible:false
    }
  },
  activated() {
    let id = this.$route.query.id
    let _this = this
    if (id) {
      _this.formLoading = true
      subjectApi.select(id).then(re => {
        this.form.directionName=re.data.directionName
        this.form.directionID=re.data.directionID
        this.form.profession.professionID=re.data.professionId
        _this.formLoading = false
      })
    } else{
      this.deleteForm()
    }
  },
  created () {
  },
  methods: {
    deleteFormConfirm(){
      this.deleteVisible=true
    },
    submitForm () {
      let _this = this
      this.formLoading = true
      this.form.profession.professionName = this.enumFormat(this.levelEnum, this.form.profession.professionID)
      subjectApi.edit(this.form).then(data => {
        if (data.code === 200) {
          _this.$message.success(data.data)
          _this.$router.push('/education/subject/list')
        } else {
          _this.$message.error(data.data)
          _this.formLoading = false
        }
      }).catch(e => {
        _this.formLoading = false
      })
    },
    //重置
    resetForm(){
      location.reload()
    },
    // 清空
    deleteForm () {
      let lastId = this.form.directionID
      this.$refs['form'].resetFields()
      this.form = {
        directionID: null,
        directionName: '',
        profession:{
          professionID: 1, // 分类id
          professionName: '' // 分类名称
        }
      }
      this.form.directionID = lastId
      this.deleteVisible=false
    },
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      levelEnum: state => state.exam.question.levelEnum
    })
  }
}
</script>
