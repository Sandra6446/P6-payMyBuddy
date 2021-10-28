<template>
  <div class="container-sm" id="addConnection">
    <Header></Header>

    <nav class="bg-light" aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item active"><a href="#/home">Home</a></li>
        <li class="breadcrumb-item"><a>Transfer</a></li>
      </ol>
    </nav>

    <form class="needs-validation" @submit="onSubmit">

      <div class="card">
        <div class="card-header">Featured</div>
        <div class="card-body">
          <h5 class="card-title">Special title treatment</h5>
          <p class="card-text">
            With supporting text below as a natural lead-in to additional
            content.
          </p>
          <a href="#" class="btn btn-primary">Go somewhere</a>
        </div>
      </div>
      
    </form>
  </div>
</template>

<script>
import MainTitle from "../components/MainTitle.vue";
import SubmitButton from "../components/SubmitButton.vue";
import EmailInput from "../components/EmailInput.vue";
import LinkButton from "../components/LinkButton.vue";
/*import ConnectionService from "../services/ConnectionService";*/

export default {
  name: "AddConnection",
  components: {
    MainTitle,
    EmailInput,
    SubmitButton,
    LinkButton,
  },
  data() {
    return {
      form: {
        email: "",
      },
    };
  },
  methods: {
    onSubmit() {
      /*     var data = {
        email: this.form.email,
      };
      alert(JSON.stringify(this.form));
      ConnectionService.submit(data)
        .then((response) => {
          alert(response);
        })
        .catch((e) => {
          alert(e);
        });*/
      this.$router.go("#/transfer");
    },
  },
};
</script>
