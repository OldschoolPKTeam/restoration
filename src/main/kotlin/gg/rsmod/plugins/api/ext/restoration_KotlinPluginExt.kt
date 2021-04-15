package gg.rsmod.plugins.api.ext

import gg.rsmod.game.model.entity.Player
import java.util.function.Predicate

data class SkillRestorationContext(val player: Player, val skill: Int)

private val predicates = mutableListOf<SkillRestorationContext.() -> Boolean>()

/**
 * Add a predicate for whether a certain skill will restore/drain.
 * @param unit A function that will return true if restoration will process on this skill, false otherwise.
 */
fun skill_restoration_predicate(unit: SkillRestorationContext.() -> Boolean) = predicates.add(unit)
fun checkSkillRestorationPredicate(skillRestorationContext: SkillRestorationContext): Boolean =
        predicates.any { it(skillRestorationContext) }