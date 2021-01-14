package com.sm9i.eyemvvm.player.render


interface IRenderView {


    companion object {

        const val AR_ASPECT_FIT_PARENT = 0  //适应父布局
        const val AR_ASPECT_FILL_PARENT = 1  //填充父布局
        const val AR_ASPECT_WRAP_CONTENT = 2  //包裹模式
        const val AR_MATCH_PARENT = 3   //匹配父布局模式
        const val AR_16_9_FIT_PARENT = 4    //16:9适配屏幕
        const val AR_4_3_FIT_PARENT = 5     //4：3适配屏幕
    }

}