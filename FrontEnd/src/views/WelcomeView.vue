<script setup>
// import {defineComponent} from 'vue'
// import {Lock, User} from "@element-plus/icons-vue";

// export default defineComponent({
//   name: "WelcomeView",
//   components: {Lock, User}
// })
</script>
<!--                src="https://images5.alphacoders.com/564/564931.jpg"/>-->
<!--                src ="https://cdn.pixabay.com/photo/2022/07/20/18/44/reading-7334749_1280.png"/>-->

<template>

  <div style="width: 100vw;height: 100vh;overflow:hidden;display: flex">

    <div style="flex: 1;">

      <el-image style="width: 100%;height: 100%" fit="cover"
                src="https://img3.wallspic.com/previews/2/1/8/5812/5812-chao_ji_ma_li_ao-shi_jie_chao_ji_ma_li_ao-cai_liao-ma_li_ao-hong_se_de-x750.jpg"/>
      <div class="welcome">
        <div style="font-size: 60px;color: black;font-weight: bold;font-family: 楷体,serif "> 欢迎你的到来！</div>
        <div
            style="font-size: 25px;color: black;font-weight: bold;margin-top: 20px;font-style: italic;font-family: 楷体,serif">
          I want you to find your own walk right now
        </div>
        <div
            style="font-size: 25px;color: black;font-weight: bold;margin-left: 100px; margin-top: 10px;font-style: italic;font-family: 楷体,serif">
          your own way of striding pacing any direction
        </div>
      </div>
    </div>

    <div style="width: 500px;background-color: white;z-index: 1 ">
      <router-view v-slot="{ Component }">
        <transition name="el-fade-in-linear" mode="out-in">
          <component :is="Component" style="height: 100%"/>
        </transition>
      </router-view>


    </div>

  </div>

</template>

<style scoped>

.welcome {

  position: absolute;
  bottom: 30px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;
}


</style>