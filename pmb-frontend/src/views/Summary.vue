<template>
  <div class="container-fluid" id="summary">
    <Header></Header>

    <nav aria-label="breadcrumb" class="bg-light">
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <router-link to="/home">Home</router-link>
        </li>
        <li class="breadcrumb-item">
          <router-link to="/transfer">Transfer</router-link>
        </li>
        <li class="breadcrumb-item active" aria-current="page">Summary</li>
      </ol>
    </nav>

    <div class="row justify-content-center">
      <div class="col-9">
        <form class="needs-validation" @submit="onSubmit">
          <div class="card my-5">
            <div class="card-header text-start fw-bold">Connection</div>
            <div class="card-body">
              <p class="card-text">Hayley</p>
              <p class="card-text">{{connectionEmail}}</p>
            </div>
          </div>

          <div class="card my-5">
            <div class="card-header text-start fw-bold">Description</div>
            <div class="card-body">
              <p class="card-text">
                <textarea class="form-control" rows="3" required></textarea>
              </p>
            </div>
          </div>

          <div class="card my-5">
            <div class="card-header text-start fw-bold">Amount</div>
            <div class="card-body">
              <p class="card-text" id="amount">{{amount}}€</p>
            </div>
          </div>

          <div class="col-8 col-sm-3 mx-auto">
            <SubmitButton lib="Send"></SubmitButton>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

 <script>
import Header from "../components/Header.vue";
import SubmitButton from "../components/SubmitButton.vue";

export default {
  name: "Summary",
  components: {
    Header,
    SubmitButton,
  },
  props: {
      connectionEmail:String,
      amount:String,
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
      this.$router.push("/transfer");
    },
  },
};
</script>

<style scoped>
.card-text {
  color: #28a745;
}
</style>

