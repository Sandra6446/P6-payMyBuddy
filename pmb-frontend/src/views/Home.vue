<template>
  <div class="container-fluid" id="home">
    
    <Header></Header>

    <nav aria-label="breadcrumb" class="bg-light">
      <ol class="breadcrumb">
        <li class="breadcrumb-item">Home</li>
      </ol>
    </nav>

    <div class="col col-10 mx-auto">
      <div class="row justify-content-md-center my-5">
        <h1>Welcome {{ name }}</h1>
      </div>
      <div class="card text-center my-5">
        <div class="card-header">Available balance</div>
        <div class="card-body">
          <p class="card-text">{{ balance }} €</p>
        </div>
      </div>

      <table
        id="last_transactions"
        class="table text-center table-striped caption-top"
      >
        <caption>
          Last transactions
        </caption>

        <thead>
          <tr>
            <th scope="col">Connection</th>
            <th scope="col">Description</th>
            <th scope="col">Amount</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in items" v-bind:key="item.id">
            <td>{{ item.connection }}</td>
            <td>{{ item.description }}</td>
            <td>{{ item.amount }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

 <script>
import Header from '../components/Header.vue';

export default {
  name: "Home",
  components: {
    Header
  },
  data() {
    return {
      name: "Laure Brun",
      balance: 20,
      items: [
        {
          id: 1,
          connection: "Hayley",
          description: "Jane's gift",
          amount: "20€",
        },
        {
          id: 2,
          connection: "Hayley",
          description: "Restaurant bill share",
          amount: "-10€",
        },
        {
          id: 3,
          connection: "Clara",
          description: "Trip money",
          amount: "-25€",
        },
        {
          id: 4,
          connection: "Smith",
          description: "Movie tickets",
          amount: "-8€",
        },
        {
          id: 5,
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
};
</script>

<style scoped>
.card-text {
  color: #28a745;
}
</style>
