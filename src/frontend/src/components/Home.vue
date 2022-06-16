<template>
    <div>
        {{'Witaj ' + user.firstName + " " + user.lastName + "!"}}
    </div>
</template>

<script>
import { mapGetters } from "vuex";

export default ({
    setup() {
        
    },
    computed: {
    ...mapGetters(['user'])
    },
})
</script>

<style scoped>

</style>