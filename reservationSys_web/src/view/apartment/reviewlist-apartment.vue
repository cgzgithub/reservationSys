<template>
    <div class="container">
        <reviewList :stage="stage"></reviewList>
    </div>
</template>
<script>
import reviewList from './component/reviewlist-model';
export default {
    name:'reviewlist-apartment',
    data(){
        return{
            stage:2
        }
    },
    components:{
        reviewList
    },
    mounted(){
    },
    methods:{
    }
}
</script>

