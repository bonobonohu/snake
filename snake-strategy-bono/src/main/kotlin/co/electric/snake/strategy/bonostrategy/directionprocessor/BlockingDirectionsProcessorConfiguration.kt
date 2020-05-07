package co.electric.snake.strategy.bonostrategy.directionprocessor

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlockingDirectionsProcessorConfiguration {

    @ConditionalOnMissingBean
    @Bean
    fun blockingDirectionsProcessor(): BlockingDirectionsProcessor {
        return BlockingDirectionsProcessor()
    }

}
