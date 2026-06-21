package dev.michaelfarrant.kcsb.core.cqrs;

public interface QueryHandler<TQuery extends Query, TResult> {

    TResult handleQuery(TQuery query);

}
