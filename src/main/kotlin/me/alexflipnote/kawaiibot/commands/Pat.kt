package me.alexflipnote.kawaiibot.commands

import com.github.natanbc.weeb4j.image.NsfwFilter
import me.alexflipnote.kawaiibot.KawaiiBot
import me.alexflipnote.kawaiibot.extensions.sendFile
import me.alexflipnote.kawaiibot.utils.Helpers
import me.aurieh.ichigo.core.CommandContext
import me.aurieh.ichigo.core.ICommand
import me.aurieh.ichigo.core.annotations.Command
import net.dv8tion.jda.core.Permission

@Command(description = "Give someone a pat! o//o", botPermissions = [Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ATTACH_FILES])
class Pat : ICommand {
    override fun run(ctx: CommandContext) {
        val m = ctx.args.asMember

        when {
            m == null ->
                ctx.send("Are you trying to pat air...?")
            m.user.idLong == ctx.jda.selfUser.idLong ->
                ctx.channel.sendFile(Helpers.getImageStream("images/kawaiipat.gif"), "kawaiipat.gif").queue()
            m.user.idLong == ctx.author.idLong -> {
                ctx.channel.sendFile("Don't be like that ;-;", Helpers.getImageStream("images/selfpat.gif"), "selfpat.gif").queue()
            }
            else -> {
                val api = KawaiiBot.wolkeApi
                api.getRandomImage("pat", null, null, NsfwFilter.NO_NSFW, null).async { image ->
                    ctx.sendEmbed {
                        setDescription("**${m.user.name}**, you got a pat from **${ctx.author.name}**")
                        setImage(image.url)
                    }
                }
            }
        }
    }
}