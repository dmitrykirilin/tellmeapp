<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title>TellMe</v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="profile">{{profile.name}}</span>
             <v-btn v-if="profile" icon>
                     <v-icon href = "/logout">mdi-exit-to-app</v-icon>
                   </v-btn>
            </div>
        </v-app-bar>
        <v-main>

        <v-container app v-if="!profile">Необходимо авторизоваться через
                <a href="/login">Google</a>
        </v-container>

        <v-container fluid v-if="profile">
                <messages-list :messages="messages"/>
        </v-container>

        </v-main>
    </v-app>
</template>

<script>
    import MessagesList from 'components/messages/MessageList.vue'
    import {addHandler} from "util/ws";
    import { getIndex} from "util/collections";

    export default {
        components:{
            MessagesList
        },
        data() {
            return {
                messages: frontendData.messages,
                profile: frontendData.profile
            }
        },
        created(){
            addHandler(data =>{
                let index = getIndex(this.messages, data.id)
                if(index > -1){
                 this.messages.splice(index, 1, data)
                } else {
                    this.messages.push(data)
                }
            })
        }
    }
</script>

<style>

</style>