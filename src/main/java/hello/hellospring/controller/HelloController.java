package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //컨트롤러 어노테이션 필수
public class HelloController {

    @GetMapping("hello") //웹 어플리케이션에서 "hello"를 치고 들어오면 아래 메소드 실행
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMVC(@RequestParam("name") String name, Model model) { //@RequestParam으로 외부에서 파라미터를 받아 옴.
        model.addAttribute("name", name); //파라미터로 넘어온 name을 모델에 넘어감 (key,value)
        return "hello-template";

    }

    @GetMapping("hello-string")
    @ResponseBody //필수! http 통신의 헤드와 바디 중 응답 바디에 아래 데이터를 직접 넣어주겠다는 의미.
    public String hellostring(@RequestParam("name") String name) {
        return "hello " + name; //hello Spring!
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{ //클래스 생성
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
