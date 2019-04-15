<template>
  <div class="quigle">
    <h1>{{ msg }}</h1>
  </div>
</template>

<script>
export default {
  name: 'Quigle',
  data () {
    return {
      msg: 'Hello Quigle'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
  border-bottom-style: dot-dot-dash;
  color: fuchsia;
}

</style>
