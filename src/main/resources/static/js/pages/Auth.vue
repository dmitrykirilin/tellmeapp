<template>
    <v-container app v-if="!profile">
                    Необходимо авторизоваться через
                    <a href="/login">Google</a>
            </v-container>
</template>

<script>

    export default {
        name: 'Auth'
        }
</script>

<style scoped>

</style>