<template>
  <div style="margin-top: 10px">
    <el-row class="app-item-contain">
      <el-col :span="6" :offset="4">
      <h3 class="index-title-h3" style="border-left: solid 10px cornflowerblue;">试卷列表</h3>
      </el-col>
<!--      xp:最开始的版本-->
<!--      <div style="padding-left: 15px">-->
<!--        <el-col :span="4" v-for="(item, index) in fixedPaper" :key="index" :offset="index > 0 ? 1 : 0">-->
<!--          <el-card :body-style="{ padding: '0px' }" v-loading="loading">-->
<!--            <img src="@/assets/exam-paper/show1.png" class="image">-->
<!--            <div style="padding: 14px;">-->
<!--              <span>{{item.name}}</span>-->
<!--              <div class="bottom clearfix">-->
<!--                <router-link target="_blank" :to="{path:'/do',query:{id:item.id}}">-->
<!--                  <el-button type="text" class="button">开始做题</el-button>-->
<!--                </router-link>-->
<!--              </div>-->
<!--            </div>-->
<!--          </el-card>-->
<!--        </el-col>-->
<!--      </div>-->
    </el-row>
    <el-row class="app-item-contain">
      <el-col :span="15" :offset="4">
        <el-card shadow="never">
          <el-form ref="form" :model="form" label-position="top" :rules="rules">
            <el-col :span="10" :offset="4">
              <el-form-item prop="profession">
              <span slot="label" style="font-size: 18px">
                专业分类
              </span>
                <el-select v-model="form.profession" placeholder="请选择专业" @change="changeSelect" style="width: 500px">
                  <el-option
                    v-for="(item,index) in professions"
                    :key="index"
                    :label="item"
                    :value="item"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
              <span slot="label" style="font-size: 18px">
                出题方式
              </span>
                <el-switch
                  v-model="form.has_direction"
                  @change="changeDisable"
                  active-text="自定义出题"
                  inactive-text="系统随机出题">
                </el-switch>
              </el-form-item>
              <div v-if="isDisabled">
                <el-form-item prop="directions">
                  <span slot="label" style="font-size: 18px">
                    专业方向
                  </span>
                  <el-select v-model="form.directions" multiple placeholder="请选择两个专业方向" multiple-limit="2" style="width: 500px">
                    <el-option
                      v-for="(item,index) in options"
                      :key="index"
                      :label="item"
                      :value="item">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="single_choice_num">
                  <span slot="label" style="font-size: 18px">
                    单选题数量
                  </span>
                  <el-input type="number" v-model.number="form.single_choice_num" placeholder="请输入单选题数量" style="width: 500px"></el-input>
                </el-form-item>
                <el-form-item prop="multiple_choice_num">
                  <span slot="label" style="font-size: 18px">
                    多选题数量
                  </span>
                  <el-input type="number" v-model.number="form.multiple_choice_num" placeholder="请输入多选题数量" style="width: 500px"></el-input>
                </el-form-item>
                <el-form-item prop="true_false_num">
                  <span slot="label" style="font-size: 18px">
                  判断题数量
                   </span>
                  <el-input type="number" v-model.number="form.true_false_num" placeholder="请输入判断题数量" style="width: 500px"></el-input>
                </el-form-item>
                <el-form-item prop="short_answer_num">
                  <span slot="label" style="font-size: 18px">
                  简答题数量
                  </span>
                  <el-input type="number" v-model.number="form.short_answer_num" placeholder="请输入简答题数量" style="width: 500px"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button @click="createBegin" type="primary" plain>生成试卷</el-button>
                </el-form-item>
              </div>
              <div v-if="!isDisabled">
                <el-form-item prop="single_choice_num">
                  <span slot="label" style="font-size: 18px">
                    单选题数量
                  </span>
                  <el-input type="number" v-model.number="form.single_choice_num" placeholder="请输入单选题数量" style="width: 500px"></el-input>
                </el-form-item>
                <el-form-item prop="multiple_choice_num">
                  <span slot="label" style="font-size: 18px">
                    多选题数量
                  </span>
                  <el-input type="number" v-model.number="form.multiple_choice_num" placeholder="请输入多选题数量" style="width: 500px"></el-input>
                </el-form-item>
                <el-form-item prop="true_false_num">
                  <span slot="label" style="font-size: 18px">
                  判断题数量
                   </span>
                  <el-input type="number" v-model.number="form.true_false_num" placeholder="请输入判断题数量" style="width: 500px"></el-input>
                </el-form-item>
                <el-form-item prop="short_answer_num">
                  <span slot="label" style="font-size: 18px">
                  简答题数量
                  </span>
                  <el-input type="number" v-model.number="form.short_answer_num" placeholder="请输入简答题数量" style="width: 500px"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button @click="createBegin" type="primary" plain>生成试卷</el-button>
                </el-form-item>
              </div>
            </el-col>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import indexApi from '@/api/dashboard'
export default {
  data () {
    return {
      /*
      fixedPaper: [],
      timeLimitPaper: [],
      pushPaper: [],
      loading: false,
      taskLoading: false,
      taskList: [],
      name
      */
      form: {
        profession: '',
        has_direction: true,
        directions: '',
        single_choice_num: '',
        multiple_choice_num: '',
        true_false_num: '',
        short_answer_num: ''
      },
      professions: ['毛概'],
      directions: {
        '毛概': ['经济', '民生', '党建', '生态', '习思想']
      },
      options: [],
      isDisabled: true,
      rules: {
        profession: [
          { required: true, message: '请选择专业', trigger: 'blur' }
        ],
        directions: [
          { type: 'array', required: true, message: '请选择两个专业方向', trigger: 'blur' }
        ],
        single_choice_num: [
          { required: true, message: '请输入单选题数量', trigger: 'blur' }
        ],
        multiple_choice_num: [
          { required: true, message: '请输入多选题数量', trigger: 'blur' }
        ],
        true_false_num: [
          { required: true, message: '请输入判断题数量', trigger: 'blur' }
        ],
        short_answer_num: [
          { required: true, message: '请输入简答题数量', trigger: 'blur' }
        ]
      },
      get: []
    }
  },
  created () {
    /*
    let _this = this
    this.loading = true
    indexApi.index().then(re => {
      _this.fixedPaper = re.response.fixedPaper
      _this.timeLimitPaper = re.response.timeLimitPaper
      _this.pushPaper = re.response.pushPaper
      _this.loading = false
    })

    this.taskLoading = true
    indexApi.task().then(re => {
      _this.taskList = re.response
      _this.taskLoading = false
    })
    */
  },
  methods: {
    statusTagFormatter (status) {
      return this.enumFormat(this.statusTag, status)
    },
    statusTextFormatter (status) {
      return this.enumFormat(this.statusEnum, status)
    },
    changeSelect () {
      this.form.direction = ''

      for (const k in this.professions) {
        if (this.form.profession === this.professions[k]) {
          this.options = this.directions[this.form.profession]
        }
      }
    },
    changeDisable () {
      this.isDisabled = !this.isDisabled
    },
    createBegin () {
      let _this = this
      this.$refs.form.validate((valid) => {
        if (valid) {
          /*
            name: 'ExamPaperBegin',
            params: {
              profession: this.form.profession,
              has_direction: this.form.has_direction,
              directions: this.form.directions,
              single_choice_num: this.form.single_choice_num,
              multiple_choice_num: this.form.multiple_choice_num,
              true_false_num: this.form.true_false_num,
              short_answer_num: this.form.short_answer_num,
              get: this.get
            }
           */
          indexApi.create(JSON.stringify(this.form)).then(data => {
            if (data.code === 200) {
              _this.$message.success(data.message)
              this.get = data.data
              this.$router.push({
                path: '/begin',
                query: {
                  profession: this.form.profession,
                  has_direction: this.form.has_direction,
                  directions: this.form.directions,
                  single_choice_num: this.form.single_choice_num,
                  multiple_choice_num: this.form.multiple_choice_num,
                  true_false_num: this.form.true_false_num,
                  short_answer_num: this.form.short_answer_num,
                  get: this.get
                }
              })
            } else {
              _this.$message.error(data.message)
            }
          })
        } else {
          return false
        }
      })
    }
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      statusEnum: state => state.exam.examPaperAnswer.statusEnum,
      statusTag: state => state.exam.examPaperAnswer.statusTag
    })
  }
}
</script>

<style lang="scss" scoped>
  .index-title-h3 {
    font-size: 22px;
    font-weight: 400;
    color: #1f2f3d;
    border-left: solid 10px #2ce8b4;
    padding-left: 10px;
  }

  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 50%;
    height: 80%;
    display: block;
    margin: 20px auto 10px auto;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .formLabel {
    font-size: 20px;
    color: #333333;
    font-family: sans-serif;
    font-weight: 200;
    line-height: 30px;
  }
</style>
