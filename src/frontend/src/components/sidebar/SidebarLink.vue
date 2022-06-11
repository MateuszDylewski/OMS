<template>
  <router-link :to="to" class="link" :class="{ active: isActive }">
    <fa class="icon" :icon="icon" size="lg"/>
    <slot  />
  </router-link>
</template>

<script>
import {computed} from "vue";
import { useRoute } from "vue-router";

export default {
  props: {
    to: {type: String, required: true},
    icon: {type: String, required: true}
  },
  setup(props) {
    const route = useRoute()
    const isActive = computed(() => route.path === props.to)
    return {isActive}
  }
}
</script>

<style scoped>

.link {
  display: flex;
  align-items: center;

  cursor: pointer;
  position: relative;
  user-select: none;

  padding: 12px;
  margin-bottom: 2px;
  margin-top: 2px;

  border-radius: 4px;

  color: white;
  text-decoration: none;
}

.link:hover {
  background-color: var(--item-hover);
}

.link.active {
  background-color: var(--item-active);
  font-weight: 700;
}

.link .icon {
  flex-shrink: 0;
  width: 25px;
  margin-right: 10px;
}
</style>