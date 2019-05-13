import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);
function getCookieValue(a) {
    var b = document.cookie.match('(^|[^;]+)\\s*' + a + '\\s*=\\s*([^;]+)');
    console.log(document.cookie)
    return b ? b.pop() : '';
}
export const store = new Vuex.Store({
    state: {
        token: getCookieValue('token'),
        urls:{
            login: 'login',
            validate: 'validate',
            accounts: 'products/getAccounts',
            transactions: 'transactions/getTransactions/{account}'
        }
    },
    mutations:{
        setToken: (state, token)=>{
            document.cookie = "token="+token;
            state.token= token;
            console.log(state.token);
        }
    }
});