package com.nyatetduwit.domain.usecase.transaction;

import com.nyatetduwit.domain.repository.TransactionRepository;
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
public final class GetTopCategoriesByExpenseUseCase_Factory implements Factory<GetTopCategoriesByExpenseUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public GetTopCategoriesByExpenseUseCase_Factory(
      Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTopCategoriesByExpenseUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTopCategoriesByExpenseUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new GetTopCategoriesByExpenseUseCase_Factory(repositoryProvider);
  }

  public static GetTopCategoriesByExpenseUseCase newInstance(TransactionRepository repository) {
    return new GetTopCategoriesByExpenseUseCase(repository);
  }
}
