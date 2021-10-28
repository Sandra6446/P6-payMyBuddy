<template>
  <div class="container-fluid" id="transfer">
    <Header></Header>

    <nav aria-label="breadcrumb" class="bg-light">
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <router-link to="/home" replace>Home</router-link>
        </li>
        <li class="breadcrumb-item active" aria-current="page">Transfer</li>
      </ol>
    </nav>

    <div class="row justify-content-center">
      <div class="col-9">
        <div class="row align-items-center justify-content-between">
          <div class="col-auto text-center">Send Money</div>
          <div class="col-auto">
            <router-link to="/connection">
              <button type="button" class="btn btn-primary">
                Add Connection
              </button>
            </router-link>
          </div>
        </div>

        <form class="needs-validation" @submit="onSubmit">
          <div
            class="card h-100 text-dark bg-light mb-3 justify-content-center"
          >
            <div class="card-body">
              <div class="row align-items-center justify-content-center">
                <div class="col-4">
                  <select
                    class="form-select form-select-md"
                    aria-label="Select connection"
                  >
                    <option selected>Connection</option>
                    <option value="1">Hayley</option>
                    <option value="2">Smith</option>
                    <option value="3">Clara</option>
                  </select>
                </div>
                <div class="col-2">
                  <input
                    type="number"
                    class="form-control"
                    value="0"
                    aria-label="Amount"
                  />
                </div>
                <div class="d-grid col-2">
                  <button class="btn col-12" id="payButton" type="submit">
                    Pay
                  </button>
                </div>
              </div>
            </div>
          </div>
        </form>

        <table
          id="last_transactions"
          class="table text-center table-striped caption-top"
        >
          <caption>
            My transactions
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
  </div>
</template>

 <script>
import Header from "../components/Header.vue";

export default {
  name: "Transfer",
  components: {
    Header,
  },
  data() {
    return {
      items: [
        {
          id: 1,
          connection: "Hayley",
          description: "Restaurant bill share",
          amount: "10€",
        },
        {
          id: 2,
          connection: "Hayley",
          description: "Trip money",
          amount: "25€",
        },
        {
          id: 3,
          connection: "Smith",
          description: "Movie tickets",
          amount: "8€",
        },
      ],
    };
  },
  methods: {
    onSubmit() {
      /* var data = {
        email: this.form.email,
        password: this.form.password,
      };
      alert(JSON.stringify(data));
     LoginService.submit(data)
        .then((response) => {
          alert(response);
        })
        .catch((e) => {
          alert(e);
        });
        */
      this.$router.push("/summary");
    },
  },
};
</script>

 <style scoped>
form {
  height: 8rem;
}

form .row {
  height: 8rem;
}

.input-symbol-euro {
  position: relative;
}
.input-symbol-euro input {
  padding-left: 18px;
}

.input-symbol-euro:before {
  position: absolute;
  top: 8px;
  content: "€";
  right: 40px;
}

input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  opacity: 1;
}

#payButton {
  background: #28a745;
  color: white;
}
</style>

