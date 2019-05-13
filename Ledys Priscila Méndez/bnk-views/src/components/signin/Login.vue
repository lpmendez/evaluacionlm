<template>
<div class ="container">
    <div class="login-page">
        <div class="form">
          <h3>Login</h3>
          <form class="login-form">
            <input type="text" v-model="username" @keydown="validateUsr($event)" placeholder="username"/>
            <input type="password" v-model="password" @keydown="validatePwd($event)" placeholder="password"/>
            <button @click="signIn">Ingresar</button>
            <p class="message">{{ msg }}</p>
          </form>
        </div>
    </div>
</div>
</template>

<script>
import {http} from '../../axios/common';
import {mapState, mapMutations} from 'vuex';
/* eslint-disable */
export default {
    data(){
        return{
            username: '',
            password: '',
            msg: ''
        }
    },
    computed: mapState(['urls']),
    methods:{
        ...mapMutations(['setToken']),
        signIn: function(){
            let request = {
                username: this.username,
                password: this.password
            };

            http.post(this.urls.login, request)
            .then((response) => {
                console.log(response.headers.authorization);
                this.setToken(response.headers.authorization);
                this.$router.replace('/');
            })
            .catch((error) => {
                console.log(error);
                let status = error.response.status;
                if(status == '401'){
                    this.msg = 'Credenciales Invalidas'
                }
                else if(status == '400'){
                    this.msg = 'Usuario Inactivo, favor contactar con soporte'
                }
            });
        },
        validateUsr: (e)=>{
            
            const control= [8,36,35,46,37,38,39,40,13,9];
            if(control.includes(e.keyCode))
                return true;
            var re = /^[a-zA-Z\d]$/;
            const found = e.key.match(re);
            if(!found){
                e.preventDefault();
            }
            else
                return true;
        },
        validatePwd: (e)=>{
            
            const control= [8,36,35,46,37,38,39,40,13,9];
            if(control.includes(e.keyCode))
                return true;
            
            var re = /^[a-zA-Z\d\*\@\-\_\.\#]$/;
            const found = e.key.match(re);
            if(!found){
                e.preventDefault();
            }
            else
                return true;
        }
    }
}
</script>

<style scoped src="./style.css"></style>
