<template>
    <div class="card">
        <slot></slot>
    </div>
</template>

<style scoped>
    .card {
        border-radius: 12px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.26);
        padding-right:0.5rem;
        padding-left: 0.5rem;
        margin-left: 5px;
        margin-bottom: 2px;
        max-width: 75rem;
       max-height:45rem;
        overflow-x: hidden;
        overflow-y: hidden;
       padding-top: 5px;

    }
</style>