package gg.rsmod.plugins.content.mechanics.restoration

val SKILL_RESTORATION_TIMER = TimerKey()

on_login { resetRestorationTimer(player) }

on_timer(SKILL_RESTORATION_TIMER) {
    player.decayOrRestore()
    resetRestorationTimer(player)
}

/**
 * Restores the players skills based on a global player timer
 */
fun Player.decayOrRestore() {
    val player = this
    with(player.getSkills()) {
        skills
            .forEach { skill ->
                val maxLevel = getMaxLevel(skill.id)
                val currentLevel = getCurrentLevel(skill.id)
                if (!checkSkillRestorationPredicate(SkillRestorationContext(player, skill.id))) {
                    return@forEach
                }
                when {
                    maxLevel > currentLevel -> incrementCurrentLevel(skill.id, 1, true)
                    maxLevel < currentLevel -> incrementCurrentLevel(skill.id, -1, false)
                }
            }
    }
}

fun resetRestorationTimer(player: Player) = player.timers.set(SKILL_RESTORATION_TIMER, 60.secondsToTicks())