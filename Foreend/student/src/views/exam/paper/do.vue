<template>
  <div>
    <h1 style="text-align: center">{{profession}}考试</h1>
    <div v-if="directions.length==0">
      <h3 style="text-align: center">全部</h3>
    </div>
    <div v-if="directions.length>0">
      <h3 style="text-align: center">{{directions[0]}}&{{directions[1]}}</h3>
    </div>
    <h4 style="text-align: center">考试时间：120分钟</h4>
    <el-row :gutter="10">
      <el-col :span="6" :offset="1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="index-title-h3" style="border-left: solid 5px #6767cb;">答题卡</span>
          </div>
          <el-row>
            <el-button class="button_style" v-for="o in get.length" :key="o" @click="Submit(o+1)">
              {{o}}
            </el-button>
          </el-row>
          <el-row>
            <el-button class="el-button" round type="primary" @click="open">提交试卷</el-button>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card style="width: 920px">
          <!-- 单选题 -->
          <div class="clearfix" v-if="single_choice_num>0">
            <span class="index-title-h3" style="border-left: solid 5px #8c939d;">单选题(共{{single_choice_num}}题)</span>
            <div v-for="(item,index) in single_choice_list.slice(0,single_choice_index)" :key="index">
              <p class="p-style" :class="`classabc${index+1}`">{{index+1}}.{{item.body}}</p>
              <el-radio-group v-model="item.radio">
                <el-row>
                  <el-radio :label="1" class="el-radio">{{item.choice[0]}}</el-radio>
                </el-row>
                <el-row>
                  <el-radio :label="2" class="el-radio">{{item.choice[1]}}</el-radio>
                </el-row>
                <el-row>
                  <el-radio :label="3" class="el-radio">{{item.choice[2]}}</el-radio>
                </el-row>
                <el-row>
                  <el-radio :label="4" class="el-radio">{{item.choice[3]}}</el-radio>
                </el-row>
              </el-radio-group>
            </div>
            <el-divider></el-divider>
          </div>
          <!-- 多选题 -->
          <div class="clearfix" v-if="multiple_choice_num>0">
            <span class="index-title-h3" style="border-left: solid 5px #8c939d;">多选题(共{{multiple_choice_num}}题)</span>
            <div v-for="(item,index) in multiple_choice_list.slice(0,multiple_choice_index)" :key="index">
              <p class="p-style" :class="`classabc${index+1}`">{{index+1+single_choice_index}}.{{item.body}}</p>
              <el-row v-for="choice in item.choice" :key="choice">
                <el-checkbox :label="choice"></el-checkbox>
              </el-row>
            </div>
            <el-divider></el-divider>
          </div>
          <!-- 判断题 -->
          <div class="clearfix" v-if="true_false_num>0">
            <span class="index-title-h3" style="border-left: solid 5px #8c939d;">判断题(共{{true_false_num}}题)</span>
            <div v-for="(item,index) in true_false_list.slice(0,true_false_index)" :key="index">
              <p class="p-style" :class="`classabc${index+1}`">{{index+1+single_choice_index+multiple_choice_index}}.{{item.body}}</p>
              <el-radio-group v-model="item.radio">
                <el-row>
                  <el-radio :label="5" class="el-radio">正确</el-radio>
                </el-row>
                <el-row>
                  <el-radio :label="6" class="el-radio">错误</el-radio>
                </el-row>
              </el-radio-group>
            </div>
            <el-divider></el-divider>
          </div>
          <!-- 简答题 -->
          <div class="clearfix" v-if="short_answer_num>0">
            <span class="index-title-h3" style="border-left: solid 5px #8c939d;">简答题(共{{short_answer_num}}题)</span>
            <div v-for="(item,index) in short_answer_list.slice(0,short_answer_index)" :key="index">
              <p class="p-style" :class="`classabc${index+1}`">{{index+1+single_choice_index+multiple_choice_index+true_false_index}}.{{item}}</p>
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                placeholder="请输入内容"
                v-model="textarea1">
              </el-input>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import QuestionEdit from '../components/QuestionEdit'
/* eslint-disable */


export default {
  components: { QuestionEdit },
  data () {
    return {
      get: [],
      profession: '',
      directions: '',
      single_choice_num: '',
      multiple_choice_num: '',
      true_false_num: '',
      short_answer_num: '',
      single_choice_index: 0,
      single_choice_list: [{
        body: '',
        choice: [],
        radio: ''
      },{
        body: '',
        choice: [],
        radio: ''
      }],
      multiple_choice_index: 0,
      multiple_choice_list: [{
        body: '',
        choice: []
      },{
        body: '',
        choice: []
      }],
      true_false_index: 0,
      true_false_list: [{
        radio: '',
        body: ''
      },{
        radio: '',
        body: ''
      }],
      short_answer_index: 0,
      short_answer_list: [],
      textarea1: ''
    }
  },

  created () {
    this.get = this.$route.query.get
    this.profession = this.$route.query.profession
    this.directions = this.$route.query.directions
    this.single_choice_num = this.$route.query.single_choice_num
    this.multiple_choice_num = this.$route.query.multiple_choice_num
    this.true_false_num = this.$route.query.true_false_num
    this.short_answer_num = this.$route.query.short_answer_num
    for (let i=0; i < this.get.length; i++)
    {
      if(this.get[i].type === '1')
      {
        this.single_choice_list[this.single_choice_index].body = this.get[i].body
        this.single_choice_list[this.single_choice_index].choice = this.get[i].selection.split('$')
        this.single_choice_index++
      }
      else if (this.get[i].type === '2')
      {
        this.multiple_choice_list[this.multiple_choice_index].body = this.get[i].body
        this.multiple_choice_list[this.multiple_choice_index].choice = this.get[i].selection.split('$')
        this.multiple_choice_index++
      }
      else if(this.get[i].type === '3')
      {
        this.true_false_list[this.true_false_index].body = this.get[i].body
        this.true_false_index++
      }
      else if (this.get[i].type === '4')
      {
        this.short_answer_list[this.short_answer_index] = this.get[i].body
        this.short_answer_index++
      }
    }
  },
  mounted () {
  },
  methods: {
    Submit(i) {
      var wei = "classabc" + i
      var el = document.getElementsByClassName(wei)[0];
      console.log(el.offsetTop);
      this.$nextTick(function() {
        window.scrollTo({
          "behavior": "smooth",
          "top": el.offsetTop
        });
      });
  },
    open() {
      const h = this.$createElement;
      this.$message({
        message: h('p', null, [
          h('span', null, '提交成功！')
        ])
      });
      this.$router.push('/index')
  },
  computed: {
    ...mapGetters('enumItem', ['enumFormat']),
    ...mapState('enumItem', {
      doCompletedTag: state => state.exam.question.answer.doCompletedTag
    })
  }
}
}
</script>

<style lang="scss" scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 300px;
}

.index-title-h3 {
  font-size: 20px;
  font-weight: 400;
  color: #1f2f3d;
  border-left: solid 10px #2ce8b4;
  padding-left: 10px;
}

.button_style {
  margin-top: 10px;
  margin-left: 18px;
  margin-left: 10px;
  width: 60px;
  text-align: center;
}

.el-button {
  margin-top: 10px;
  margin-left: 18px;
  margin-left: 10px;
  width: 100px;
  text-align: center;
}

.p-style {
  font-size: 16px;
}

.el-radio {
  font-size: 25px;
}
</style>
