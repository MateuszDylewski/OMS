<template>
    <div>
        {{'Witaj ' + user.firstName + " " + user.lastName + "!"}}
    </div>
</template>

<script>
import { mapGetters } from "vuex";

export default ({
    computed: {
    ...mapGetters(['user'])
    },
})
</script>

<style scoped>

</style>