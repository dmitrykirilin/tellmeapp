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
                <messages-list />
        </v-container>

        </v-main>
    </v-app>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import MessagesList from 'components/messages/MessageList.vue'
    import {addHandler} from "util/ws";

    export default {
        components:{
            MessagesList
        },
        computed: mapState(['profile']),
        methods: mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
        created(){
            addHandler(data =>{
                if(data.objectType === 'MESSAGE'){
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateMessageMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removeMessageMutation(data.body)
                            break
                        default:
                            console.log(`Unknown event type "${data.eventType}"`)
                        }
                    } else {
                    console.error(`Object type unknown "${data.objectType}"`)
                    }
            })
        }
    }
</script>

<style>

</style>