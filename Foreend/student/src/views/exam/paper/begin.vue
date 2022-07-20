<template>
  <div>
    <el-row class="app-item-contain">
      <el-col :offset="4">
        <h4>试卷列表/{{profession}}</h4>
        <el-card class="box-card">
          <el-row>
            <span style="font-size: 22px">
            {{profession}}
            </span>
            <i class="el-icon-edit"></i>
            <el-tag type="info" class="el-tag">总分:100</el-tag>
          </el-row>
          <p style="font-size: 13px" class="p-color">更新于2022-7-18</p>
          <el-button type="primary" plain class="el-button" @click="createPaper">开始答题</el-button>
          <el-row></el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-row class="app-item-contain">
      <el-col :offset="4">
        <el-card class="box-card">
          <div v-if="directions.length==0" slot="header" class="clearfix" style="font-size: 18px">
            <span>全部</span>
          </div>
          <div v-if="directions.length>0" slot="header" class="clearfix" style="font-size: 18px">
            <span>{{directions[0]}}&{{directions[1]}}</span>
          </div>
          <p class="p">单选题(共{{single_choice_num}}题)</p>
          <el-divider></el-divider>
          <p class="p">多选题(共{{multiple_choice_num}}题)</p>
          <el-divider></el-divider>
          <p class="p">判断题(共{{true_false_num}}题)</p>
          <el-divider></el-divider>
          <p class="p">简答题(共{{short_answer_num}}题)</p>
          <el-divider></el-divider>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data () {
    return {
      profession: '',
      has_direction: '',
      directions: '',
      single_choice_num: '',
      multiple_choice_num: '',
      true_false_num: '',
      short_answer_num: '',
      questionID: '',
      direction: '',
      body: '',
      answer: '',
      type: '',
      selection: '',
      get: []
    }
  },
  created () {
    /*
    this.profession = this.$route.params.profession
    this.has_direction = this.$route.params.has_direction
    this.directions = this.$route.params.directions
    this.single_choice_num = this.$route.params.single_choice_num
    this.multiple_choice_num = this.$route.params.multiple_choice_num
    this.true_false_num = this.$route.params.true_false_num
    this.short_answer_num = this.$route.params.short_answer_num
     */
    this.profession = this.$route.query.profession
    this.has_direction = this.$route.query.has_direction
    this.directions = this.$route.query.directions
    this.single_choice_num = this.$route.query.single_choice_num
    this.multiple_choice_num = this.$route.query.multiple_choice_num
    this.true_false_num = this.$route.query.true_false_num
    this.short_answer_num = this.$route.query.short_answer_num
    this.get = this.$route.query.get
  },
  methods: {
    createPaper () {
      this.$router.push({
        path: '/do',
        query: {
          get: this.get,
          profession: this.profession,
          directions: this.directions,
          single_choice_num: this.$route.query.single_choice_num,
          multiple_choice_num: this.$route.query.multiple_choice_num,
          true_false_num: this.$route.query.true_false_num,
          short_answer_num: this.$route.query.short_answer_num
        }
      })
    }
  }
}
</script>

<style scoped>
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
  width: 800px;
}

.el-tag {
  position: absolute;
  right: 10px;
  top: 10px;
  width: 80px;
  height: 30px;
  font-size: 15px;
  font-weight: bold;
}

.p-color {
  color: #8c939d;
}

.p {
  color: #8c939d;
  font-weight: bold;
  font-size: 16px;
}

.el-button {
  position: relative;
  left: 660px;
}
</style>
