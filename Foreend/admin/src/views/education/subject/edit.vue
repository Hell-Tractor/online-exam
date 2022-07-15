<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading">
      <el-form-item label="专业方向：" required>
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="专业分类：" required>
        <el-select v-model="form.professionID" placeholder="专业分类">
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
import subjectApi from '@/api/subject'

export default {
  data () {
    return {
      form: {
        id: null, // 专业方向id
        name: '', // 专业方向名称
        professionID: 1, // 分类id
        professionName: '' // 分类名称
      },
      formLoading: false
    }
  },
  created () {
    let id = this.$route.query.id
    let _this = this
    if (id && parseInt(id) !== 0) {
      _this.formLoading = true
      subjectApi.select(id).then(re => {
        _this.form = re.response
        _this.formLoading = false
      })
    }
  },
  methods: {
    submitForm () {
      let _this = this
      this.formLoading = true
      this.form.professionName = this.enumFormat(this.levelEnum, this.form.professionID)
      subjectApi.edit(this.form).then(data => {
        if (data.code === 200) {
          _this.$message.success(data.message)
          _this.delCurrentView(_this).then(() => {
            _this.$router.push('/education/subject/list')
          })
        } else {
          _this.$message.error(data.message)
          _this.formLoading = false
        }
      }).catch(e => {
        _this.formLoading = false
      })
    },
    resetForm () {
      let lastId = this.form.id
      this.$refs['form'].resetFields()
      this.form = {
        id: null,
        name: '',
        professionID: 1,
        professionName: ''
      }
      this.form.id = lastId
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
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
