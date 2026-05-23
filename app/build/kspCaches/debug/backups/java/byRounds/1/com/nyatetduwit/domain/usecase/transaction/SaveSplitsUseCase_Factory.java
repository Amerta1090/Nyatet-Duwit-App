package com.nyatetduwit.domain.usecase.transaction;

import com.nyatetduwit.domain.repository.TransactionSplitRepository;
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
public final class SaveSplitsUseCase_Factory implements Factory<SaveSplitsUseCase> {
  private final Provider<TransactionSplitRepository> repositoryProvider;

  public SaveSplitsUseCase_Factory(Provider<TransactionSplitRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaveSplitsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaveSplitsUseCase_Factory create(
      Provider<TransactionSplitRepository> repositoryProvider) {
    return new SaveSplitsUseCase_Factory(repositoryProvider);
  }

  public static SaveSplitsUseCase newInstance(TransactionSplitRepository repository) {
    return new SaveSplitsUseCase(repository);
  }
}
