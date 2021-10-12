<template>
  <div>
    <nav class="navbar navbar-expand-lg">
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
        <div class="collapse navbar-collapse" id="navbarHeader">
          <div class="navbar-nav">
            <a class="nav-link active" aria-current="page" href="#/home">Home</a>

            <a class="nav-link" href="#/transfer">Transfer</a>

            <a class="nav-link" href="#/profile">Profile</a>

            <a class="nav-link" href="#/contact">Contact</a>

            <a class="nav-link" href="#/">Log off</a>
          </div>
        </div>
      </div>
    </nav>

    <nav class="bg-light" aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#/home">Home</a></li>
        <li class="breadcrumb-item active" aria-current="page">
          {{ currentPage }}
        </li>
      </ol>
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
  props: {
    currentPage: {
      type: String,
    },
  },
};
</script>