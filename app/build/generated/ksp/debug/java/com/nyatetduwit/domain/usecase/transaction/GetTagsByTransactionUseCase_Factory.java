package com.nyatetduwit.domain.usecase.transaction;

import com.nyatetduwit.domain.repository.TransactionTagRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class GetTagsByTransactionUseCase_Factory implements Factory<GetTagsByTransactionUseCase> {
  private final Provider<TransactionTagRepository> repositoryProvider;

  public GetTagsByTransactionUseCase_Factory(
      Provider<TransactionTagRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTagsByTransactionUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTagsByTransactionUseCase_Factory create(
      Provider<TransactionTagRepository> repositoryProvider) {
    return new GetTagsByTransactionUseCase_Factory(repositoryProvider);
  }

  public static GetTagsByTransactionUseCase newInstance(TransactionTagRepository repository) {
    return new GetTagsByTransactionUseCase(repository);
  }
}
