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
        <router-view></router-view>
        </v-main>
    </v-app>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import {addHandler} from "util/ws";

    export default {
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
        },
        beforeMount() {
                    if (!this.profile) {
                        this.$router.replace('/auth')
                    }
                }
    }
</script>

<style>

</style>