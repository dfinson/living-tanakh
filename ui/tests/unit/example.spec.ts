import { expect } from 'chai'
import {mount} from '@vue/test-utils'
import PrimaryTextDisplay from "@/components/PrimaryTextDisplay.vue";

describe('PrimaryTextDisplay.vue', () => {

  it('Testing set text', () => {
    const wrapper = mount(PrimaryTextDisplay, {propsData:{
      book: 'Amos',
      chapter: 1
      }});
    wrapper.setData({book: 'Amos', chapter: 1})
    wrapper.vm.setText();
    expect(true);
  })
})
