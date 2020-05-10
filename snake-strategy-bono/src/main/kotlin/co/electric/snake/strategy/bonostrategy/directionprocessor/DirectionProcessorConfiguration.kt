package co.electric.snake.strategy.bonostrategy.directionprocessor

import co.electric.snake.strategy.bonostrategy.directionprocessor.closeddirections.ClosedDirectionsCollector
import co.electric.snake.strategy.bonostrategy.directionprocessor.closeddirections.ClosedDirectionsProcessor
import co.electric.snake.strategy.bonostrategy.directionprocessor.closeddirections.FreeCoordinateCountsProcessor
import co.electric.snake.strategy.bonostrategy.directionprocessor.distanceprocessor.DistanceProcessorChain
import co.electric.snake.strategy.bonostrategy.directionprocessor.distanceprocessor.DistanceProcessorConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(DistanceProcessorConfiguration::class)
class DirectionProcessorConfiguration {

    @ConditionalOnMissingBean
    @Bean
    fun freeDirectionsProcessor(): FreeDirectionsProcessor {
        return FreeDirectionsProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun freeCoordinateCountsProcessor(): FreeCoordinateCountsProcessor {
        return FreeCoordinateCountsProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun closedDirectionsCollector(): ClosedDirectionsCollector {
        return ClosedDirectionsCollector()
    }

    @ConditionalOnMissingBean
    @Bean
    fun closedDirectionsProcessor(freeCoordinateCountsProcessor: FreeCoordinateCountsProcessor, closedDirectionsCollector: ClosedDirectionsCollector): ClosedDirectionsProcessor {
        return ClosedDirectionsProcessor(freeCoordinateCountsProcessor, closedDirectionsCollector)
    }

    @ConditionalOnMissingBean
    @Bean
    fun filteredDirectionsProcessor(): FilteredDirectionsProcessor {
        return FilteredDirectionsProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun equivalentBestDirectionsProcessor(): EquivalentBestDirectionsProcessor {
        return EquivalentBestDirectionsProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun blockingDirectionsProcessor(distanceProcessorChain: DistanceProcessorChain): BlockingDirectionsProcessor {
        return BlockingDirectionsProcessor(distanceProcessorChain)
    }

}
