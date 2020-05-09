package co.electric.snake.strategy.bonostrategy.distanceprocessor

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DistanceProcessorConfiguration {

    @ConditionalOnMissingBean
    @Bean
    fun northLookingDistanceProcessor(): NorthLookingDistanceProcessor {
        return NorthLookingDistanceProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun southLookingDistanceProcessor(): SouthLookingDistanceProcessor {
        return SouthLookingDistanceProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun eastLookingDistanceProcessor(): EastLookingDistanceProcessor {
        return EastLookingDistanceProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun westLookingDistanceProcessor(): WestLookingDistanceProcessor {
        return WestLookingDistanceProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun distanceProcessorChain(distanceProcessors: MutableSet<DistanceProcessor>): DistanceProcessorChain {
        return DistanceProcessorChain(distanceProcessors)
    }

}
