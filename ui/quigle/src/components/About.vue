<template>
  <div class="about">
    <h1>{{ msg }}</h1>
  </div>
</template>

<script>
export default {
  name: 'About',
  data () {
    return {
      msg: 'Quigle is named by my daughter \'静伊\', which means Quiet Angle.'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
  color: red;
}

</style>
