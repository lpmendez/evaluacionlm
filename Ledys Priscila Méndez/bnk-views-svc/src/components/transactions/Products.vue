<template>
    <div>
        <label for="type">Tipo de producto</label>
        <select v-model="type">
            <option value="-">Seleccione</option>
            <option v-for="(value, name) in types" :key="name" :value="name">
                {{name.charAt(0).toUpperCase() + name.slice(1)}}
            </option>
        </select>
        <br>
        <br>
        <label for="account">Cuenta</label>
        <select v-model="account">
            <option value="-">Seleccione</option>
            <option v-for="a in accounts" :key="a.id" :value="a.id">
                {{a.name}}
            </option>
        </select>
        <br>
        <br>
        <datepicker placeholder="Desde" 
                    input-class="dates" 
                    v-model="start"
                    format="dd-MMM-yyyy"
                    :disabled-dates="disabledDates">
        </datepicker>
        
        <datepicker placeholder="Hasta" 
                    input-class="dates" 
                    v-model="end"
                    format="dd-MMM-yyyy"
                    :disabled-dates="disabledDates">
        </datepicker>
        <button @click="getTransactions">Ver transacciones</button>
        <br>
        <br>
        <p class="message">{{ msg }}</p>
        <Transactions :transactions="transactions"/>
    </div>
</template>

<script>
import {http} from '../../axios/common';
import {mapState} from 'vuex';
import Transactions from './Transactions.vue';
import Datepicker from 'vuejs-datepicker';

export default {
    components:{
        Transactions,
        Datepicker
    },
    data(){
        return{
            types: null,
            type: '-',
            account: '-',
            start: '',
            end: '',
            transactions: [],
            msg: '',
            monthsEn: ['Jan', 'Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],
            separator: '-',
            disabledDates: {
                from: new Date()
            }
        }
    },
    computed: {
        ...mapState(['urls','token']),
        accounts(){
            this.account = '-';
            if(this.types && this.type && this.type != '-'){
                return this.types[this.type];
            }
        }
    },
    methods: {
        getTransactions(){
            this.transactions = [];
            if(!this.account || this.account == '-' || !this.start || !this.end 
            || !this.type || this.type == '-'){
                this.msg = "Campos faltantes";
                return;
            }

            //validacion de fechas
            if(!this.validateDates()) return;

            //parse de tlas fechas
            let startString = this.start.getDate()+
                        this.separator+this.monthsEn[this.start.getMonth()]+
                        this.separator+this.start.getFullYear();

            let endString = this.end.getDate()+
                        this.separator+this.monthsEn[this.end.getMonth()]+
                        this.separator+this.end.getFullYear();
            

            const url = this.urls.transactions.replace('{account}', this.account)
                +'?start='+startString+'&end='+endString+'&prd='+this.type;
            console.log(url);
            http.get(url,{
                headers: {
                    Authorization: this.token
                }
            })
            .then(response=>{
                console.log(response);
                this.msg = '';
                this.transactions = response.data.transactions;
            })
            .catch(error=>{
                const status = error.response.status;
                if(status == 440)
                    this.$router.replace('/login');
                if(status == 400)
                    this.msg = 'Cuenta inválida';
                else
                    this.$router.replace('/error');
            });
            
        },
        validateDates(){

            if(this.start.getTime() > this.end.getTime()){
                this.msg = "Fechas incorrectas";
                return false;
            }
            //3 meses: 90 dias
            if(this.addDays(this.start, 90).getTime() < this.end.getTime()){
                this.msg = "No puede ser mayor a 3 meses";
                return false;
            }
            return true;
        },
        addDays(date, days) {
            let result = new Date(date);
            result.setDate(result.getDate() + days);
            return result;
        }
    },
    created(){
        http.get(this.urls.accounts,{
            headers: {
                Authorization: this.token
            }
        })
        .then(response=>{
            console.log(response);
            this.types = response.data.accounts;
        })
        .catch(error=>{
            
            const status = error.response.status;
            if(status == 440)
                this.$router.replace('/login');
            this.$router.replace('/error');
        });
    }
}
</script>

<style>
.dates{
    float:left;
    margin-right: 10px;
}
</style>
