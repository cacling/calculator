<template>
  <div class="container">
    <div class="title-div">计算公式:</div>
    <div class="msg-div">{{ expression }}</div>
    <div class="title-div">计算结果:</div>
    <div class="msg-div">{{ result }}</div>
  </div>
  <div>
    <form @submit.prevent="calculate">
      <div>
        <button type="button" @click.stop="expression += '('">(</button>
        <button type="button" @click.stop="expression += ')'">)</button>
        <button type="button" @click.stop="redo">REDO</button>
        <button type="button" @click.stop="undo">UNDO</button>
      </div>
      <div>
        <button type="button" @click.stop="expression += '+'">+</button>
        <button type="button" @click.stop="expression += '-'">-</button>
        <button type="button" @click.stop="expression += '*'">*</button>
        <button type="button" @click.stop="expression += '/'">/</button>
      </div>
      <div>
        <button type="button" @click.stop="expression += '6'">6</button>
        <button type="button" @click.stop="expression += '7'">7</button>
        <button type="button" @click.stop="expression += '8'">8</button>
        <button type="button" @click.stop="expression += '9'">9</button>
      </div>
      <div>
        <button type="button" @click.stop="expression += '2'">2</button>
        <button type="button" @click.stop="expression += '3'">3</button>
        <button type="button" @click.stop="expression += '4'">4</button>
        <button type="button" @click.stop="expression += '5'">5</button>
      </div>
      <div>
        <button type="button" @click.stop="calculate">=</button>
        <button type="button" @click.stop="expression += '.'">.</button>
        <button type="button" @click.stop="expression += '0'">0</button>
        <button type="button" @click.stop="expression += '1'">1</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: "App",
  data() {
    return {
      expression: "",
      result: "",
    };
  },
  methods: {
    calculate() {
      //this.expression = evaluate(this.expression);
      axios.post('http://localhost:8080/calc',{expr:this.expression})
      .then(response => {
        // 将响应数据赋值给
        if(response.data.respCode == '200') {
          this.result = response.data.result;
        } else {
          alert(response.data.errMsg);
        }
      })
      .catch(error => {
        console.log(error)
      })

    },
    redo() {
      axios.get('http://localhost:8080/redo')
      .then(response => {
        if(response.data.respCode == '200') {
          this.expression = response.data.expr;
          this.result = response.data.result;
        } else {
          alert(response.data.errMsg);
        }
      })
      .catch(error => {
        console.log(error)
      })
    },
    undo() {
      axios.get('http://localhost:8080/undo')
      .then(response => {
        if(response.data.respCode == '200') {
          this.expression = response.data.expr;
          this.result = response.data.result;
        } else {
          alert(response.data.errMsg);
        }
      })
      .catch(error => {
        console.log(error)
      })
    },
  },
};
</script>

<style scoped>
button {
  width: 80px;
  height: 30px;
}
.container {
  display: flex; /* 设置容器为flex布局 */
  justify-content: center; /* 设置水平居中 */
  align-items: center; /* 设置垂直居中 */
  height: 60px; /* 设置容器的高度 */
}

.title-div {
  padding: 2px 5px 2px 15px;
}
.msg-div {
  width: 150px; /* 设置div的宽度 */
  height: 20px; /* 设置div的高度 */
  background-color: rgb(192, 255, 242); /* 设置div的背景颜色 */
  border: 1px solid;
}
</style>
