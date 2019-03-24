package hello;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;

@RestController
public class GreetingController {

    private Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private RedissonClient redisson;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GET
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) throws InterruptedException, ExecutionException {

        RAtomicLong myLong = redisson.getAtomicLong("myLong");
        RFuture<Long> val = myLong.addAndGetAsync(1);

        logger.info("test {}", val.get());

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @GET
    @Cacheable(value="test", key = "#id", sync = true)
    @RequestMapping("/greeting2")
    public Greeting greeting2(@RequestParam(value="id", defaultValue="1") int id) {

        logger.info("Cache me");

        return new Greeting(1, "test");

    }

}