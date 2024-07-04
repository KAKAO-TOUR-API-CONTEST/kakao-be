package com.example.ai_jeju.generator;


import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
public class NickNameGenerator {

    private String nickname;
    List<String> adjective = Arrays.asList("행복한","쓸쓸한","따뜻한","작은","큰","맛있는","달콤한","어려운","재미있는"
    ,"훌륭한","잘생긴","예쁜","귀여운","매력적인","편리한","친절한","순수한","청결한","상냥한","예의바른","높은","먼","정직한","성실한","공정한");
    public NickNameGenerator(){

        String number = (int)(Math.random() * 99)+1 +"";
        Collections.shuffle(adjective);
        String name = "맘";
        String adj = adjective.get(0);
        this.nickname = adj+name+number;

    }

}
