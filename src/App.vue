<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #595d61;
}

.container-sm {
    max-width: 800px;
    margin-top: 30px;
    margin-bottom: 30px;
    border-style: solid;
    border-width: 1px;
    border-color: var(--bs-gray-300);
    border-radius: 0.5em;
    padding-top: 60px;
    padding-bottom: 60px;
    padding-left: 0px;
    padding-right: 0px;
}

</style>
