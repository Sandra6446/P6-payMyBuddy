<template>
  <div class="container-fluid">
    <nav class="navbar navbar-expand-lg navbar-light">
      <div class="container-fluid">
        <span class="navbar-brand"><MainTitle></MainTitle></span>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarHeader"
          aria-controls="navbarHeader"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="col-auto me-5 d-flex">
          <div class="collapse navbar-collapse" id="navbarHeader">
            <div class="navbar-nav">
              <router-link
                class="nav-link"
                :to="{ name: 'Home', params: { email:this.$route.params.email } }"
                >Home</router-link
              >
              <router-link
                class="nav-link"
                :to="{ name: 'Transfer', params: { email:this.$route.params.email } }"
                >Transfer</router-link
              >
              <router-link class="nav-link" to="/profile">Profile</router-link>
              <router-link class="nav-link" to="/contact">Contact</router-link>
              <router-link class="nav-link" to="/">Log off</router-link>
            </div>
          </div>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
import MainTitle from "../components/MainTitle.vue";

export default {
  name: "Header",
  components: {
    MainTitle,
  },
};
</script>

<style scoped>
.nav-link {
  color: gray;
}

.nav-link .active {
  color: rgb(0, 102, 255);
}
</style>
