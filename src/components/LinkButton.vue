<template>
    <button type="button" class="btn btn-link">
        <router-link size="lg" v-bind:to="page"> {{lib}} </router-link>
    </button>
</template>

<script>
    export default {
        name: 'LinkButton',
        props: {
            lib: {
                type: String
            },
            page: {
                type: String
            }
        }
    }
</script>

<style>
    a {
      color: black;
      text-decoration: underline;
    }
</style>