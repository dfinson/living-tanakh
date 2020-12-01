<template>
  <div>
    <div class="stacks_in_103am-outerwrapper">
      <div class="stacks_in_103am-container"
           id="stacks_in_103am-container"
           style="display: block;">
        <a  v-for="(image, index) in images" :key="index"
            @click="activeIndex = index"
            href="#"
            class="stacks_in_103dummylink"
            title="">
            <span
                class="stacks_in_103regular stacks_in_103am-wrapper"
                style="max-height: 220px; margin: 2px;">
                <img
                    class="stacks_in_103Montage"
                    :src="image.itemImageSrc"
                    :alt="image.alt"
                    style="display: block; max-height: 220px; left: 0; top: 0;"/>

                <div class="stacks_in_103completeimageoverlay"></div>
                <div class="stacks_in_103zoomoverlay"></div>
                <div class="stacks_in_103imageoverlay">
                  <div class="stacks_in_103overlaytext">{{images[index].title}}</div>
                </div>
            </span>
        </a>
        <div class="stacks_in_103clearer"></div>
      </div>
    </div>
    <div v-if="displayFullScreen"
        class="lb_overlay js_lb_overlay"
        style="height: 2585px; position: absolute; width: 100%; top: 0px; left: 0px; right: 0px; bottom: 0px; z-index: 1000102; background: black; opacity: 1;">
      <img :src="images[activeIndex]"
           class="stacks_in_103clone" style="height: 600px; width: auto; left: 50%; margin-left: -448px; z-index: 1000103; position: absolute; top: 932.5px;"
           :alt="images[activeIndex].alt"/>
      <div class="stacks_in_103popupcontrolbar" style="z-index: 1000110; bottom: 742px; display: block;">
        <div @click="activeIndex = -1" class="stacks_in_103xbutton"></div>
      </div>
    </div>

  </div>
</template>


<script lang='ts'>
import {Component, Vue, Prop} from 'vue-property-decorator'
import {GalleriaItemTemplate} from "@/api/dto";

@Component({})
export default class SimpleGallery extends Vue{
  @Prop({required: true})
  private images: GalleriaItemTemplate[];
  private activeIndex = -1;

  private get displayFullScreen(){
    return this.activeIndex >= 0;
  }
}

</script>


<style scoped>

</style>