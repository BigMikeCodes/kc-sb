package dev.michaelfarrant.kcsb.core.cqrs;

public interface CommandHandler<TCommand extends Command, TResult> {

    TResult handleCommand(TCommand command);

}
