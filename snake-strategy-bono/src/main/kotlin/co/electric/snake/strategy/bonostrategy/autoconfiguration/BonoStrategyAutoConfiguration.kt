package co.electric.snake.strategy.bonostrategy.autoconfiguration

import co.electric.snake.framework.model.ModifiableArena
import co.electric.snake.framework.model.ModifiableSnake
import co.electric.snake.strategy.bonostrategy.BonoStrategy
import co.electric.snake.strategy.bonostrategy.closeddirectionsprocessors.AllButMaximumsClosedDirectionsProcessor
import co.electric.snake.strategy.bonostrategy.closeddirectionsprocessors.ClosedDirectionsProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BonoStrategyAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    fun closedDirectionsProcessor(): ClosedDirectionsProcessor {
        return AllButMaximumsClosedDirectionsProcessor()
    }

    @ConditionalOnMissingBean
    @Bean
    fun bonoStrategy(closedDirectionsProcessor: ClosedDirectionsProcessor): BonoStrategy {
        return BonoStrategy(closedDirectionsProcessor)
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
