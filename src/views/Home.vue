<template>
  <div class="container-fluid" id="home">
    <Header></Header>

    <div class="col col-8 mx-auto">
      <div class="row justify-content-md-center my-5">
        <h1>Welcome {{ name }}</h1>
      </div>
      <div class="card text-center my-5">
        <div class="card-header">Available balance</div>
        <div class="card-body">
          <p class="card-text">{{ balance }} €</p>
        </div>
      </div>

      <b-table
        id="last_transactions"
        striped
        caption-top
        class="text-center"
        :items="items"
        :current-page="currentPage"
        :per-page="perPage"
      >
        <template #table-caption>Last transactions</template>
      </b-table>

      <b-pagination
        align="center"
        v-model="currentPage"
        :total-rows="totalRows"
        :per-page="perPage"
        aria-controls="last_transactions"
      ></b-pagination>
    </div>
  </div>
</template>

 <script>
import Header from "../components/Header.vue";

export default {
  name: "Home",
  components: {
    Header,
  },
  data() {
    return {
      name: "Laure Brun",
      balance: 20,
      items: [
        {
          connection: "Hayley",
          description: "Jane's gift",
          amount: "20€",
        },
        {
          connection: "Hayley",
          description: "Restaurant bill share",
          amount: "-10€",
        },
        {
          connection: "Clara",
          description: "Trip money",
          amount: "-25€",
        },
        {
          connection: "Smith",
          description: "Movie tickets",
          amount: "-8€",
        },
        {
          connection: "Clara",
          description: "Movie tickets",
          amount: "8€",
        },
      ],
      perPage: 3,
      totalRows: 1,
      currentPage: 1,
    };
  },
  mounted() {
    // Set the initial number of items
    this.totalRows = this.items.length;
  },
};
</script>

<style scoped>
.card-text {
  color: #28a745;
}
</style>
