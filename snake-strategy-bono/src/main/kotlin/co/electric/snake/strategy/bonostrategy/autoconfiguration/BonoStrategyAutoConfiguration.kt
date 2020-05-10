package co.electric.snake.strategy.bonostrategy.autoconfiguration

import co.electric.snake.framework.model.ModifiableArena
import co.electric.snake.framework.model.ModifiableSnake
import co.electric.snake.strategy.bonostrategy.BonoStrategy
import co.electric.snake.strategy.bonostrategy.directionprocessor.*
import co.electric.snake.strategy.bonostrategy.directionprocessor.closeddirections.ClosedDirectionsProcessor
import co.electric.snake.strategy.bonostrategy.newdirectionprocessor.NewDirectionProcessorChain
import co.electric.snake.strategy.bonostrategy.newdirectionprocessor.NewDirectionProcessorConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
        DirectionProcessorConfiguration::class,
        NewDirectionProcessorConfiguration::class
)
class BonoStrategyAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    fun bonoStrategy(
            freeDirectionsProcessor: FreeDirectionsProcessor,
            closedDirectionsProcessor: ClosedDirectionsProcessor,
            filteredDirectionsProcessor: FilteredDirectionsProcessor,
            equivalentBestDirectionsProcessor: EquivalentBestDirectionsProcessor,
            blockingDirectionsProcessor: BlockingDirectionsProcessor,
            newDirectionProcessorChain: NewDirectionProcessorChain
    ): BonoStrategy {
        return BonoStrategy(
                freeDirectionsProcessor, closedDirectionsProcessor, filteredDirectionsProcessor,
                equivalentBestDirectionsProcessor, blockingDirectionsProcessor,
                newDirectionProcessorChain
        )
    }

    @ConditionalOnBean(name = ["modifiableArena"])
    @Bean
    fun jack(modifiableArena: ModifiableArena, bonoStrategy: BonoStrategy): ModifiableSnake {
        return ModifiableSnake(modifiableArena, bonoStrategy, "Jack")
    }

    @ConditionalOnBean(name = ["modifiableArena"])
    @Bean
    fun jill(modifiableArena: ModifiableArena, bonoStrategy: BonoStrategy): ModifiableSnake {
        return ModifiableSnake(modifiableArena, bonoStrategy, "Jill")
    }

}
