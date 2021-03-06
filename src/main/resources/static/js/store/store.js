import Vue from 'vue'
import Vuex from 'vuex'
import serverApi from 'api/messages'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        messages,
        profile: frontendData.profile
    },
    getters: {
        sortedMessages: state => (state.messages || []).sort((a, b) => -(a.id - b.id))
    },
    mutations: {
        addMessageMutation(state, message){
            state.messages = [
                ...state.messages,
                message
            ]
        },
        updateMessageMutation(state, message){
            const updateIndex = state.messages.findIndex(item => item.id === message.id)

            state.messages = [
                ...state.messages.slice(0, updateIndex),
                message,
                ...state.messages.slice(updateIndex + 1)
            ]
        },
        removeMessageMutation(state, message){
            const removingIndex = state.messages.findIndex(item => item.id === message.id)

            if(removingIndex > -1){
                    state.messages = [
                        ...state.messages.slice(0, removingIndex),
                        ...state.messages.slice(removingIndex + 1)
                    ]
                }
        }
    },
    actions: {
        async addMessageAction({commit, state}, message) {
            const result = await serverApi.add(message)
            const data = await result.json()
            const index = state.messages.findIndex(item => item.id === data.id)

            if (index > -1) {
                commit('updateMessageMutation', data)
            } else {
                commit('addMessageMutation', data)
            }

        },
        async updateMessageAction({commit}, message) {
            const result = await serverApi.update(message)
            const data = await result.json()
            commit('updateMessageMutation', data)
        },
        async removeMessageAction({commit}, message) {
                const result = await serverApi.remove(message.id)
                        if (result.ok) {
                            commit('removeMessageMutation', message)
                        }
                    }
    }
})