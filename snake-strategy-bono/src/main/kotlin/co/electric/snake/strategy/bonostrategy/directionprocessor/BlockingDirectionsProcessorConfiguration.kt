package co.electric.snake.strategy.bonostrategy.directionprocessor

import co.electric.snake.strategy.bonostrategy.distanceprocessor.DistanceProcessorChain
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlockingDirectionsProcessorConfiguration {

    @ConditionalOnMissingBean
    @Bean
    fun blockingDirectionsProcessor(distanceProcessorChain: DistanceProcessorChain): BlockingDirectionsProcessor {
        return BlockingDirectionsProcessor(distanceProcessorChain)
    }

}
