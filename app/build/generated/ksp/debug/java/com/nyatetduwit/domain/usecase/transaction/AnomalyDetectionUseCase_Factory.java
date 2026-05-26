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
public final class AnomalyDetectionUseCase_Factory implements Factory<AnomalyDetectionUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  private final Provider<GetDailyExpenseTrendUseCase> getDailyExpenseTrendUseCaseProvider;

  private final Provider<GetTopCategoriesByExpenseUseCase> getTopCategoriesByExpenseUseCaseProvider;

  public AnomalyDetectionUseCase_Factory(Provider<TransactionRepository> repositoryProvider,
      Provider<GetDailyExpenseTrendUseCase> getDailyExpenseTrendUseCaseProvider,
      Provider<GetTopCategoriesByExpenseUseCase> getTopCategoriesByExpenseUseCaseProvider) {
    this.repositoryProvider = repositoryProvider;
    this.getDailyExpenseTrendUseCaseProvider = getDailyExpenseTrendUseCaseProvider;
    this.getTopCategoriesByExpenseUseCaseProvider = getTopCategoriesByExpenseUseCaseProvider;
  }

  @Override
  public AnomalyDetectionUseCase get() {
    return newInstance(repositoryProvider.get(), getDailyExpenseTrendUseCaseProvider.get(), getTopCategoriesByExpenseUseCaseProvider.get());
  }

  public static AnomalyDetectionUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider,
      Provider<GetDailyExpenseTrendUseCase> getDailyExpenseTrendUseCaseProvider,
      Provider<GetTopCategoriesByExpenseUseCase> getTopCategoriesByExpenseUseCaseProvider) {
    return new AnomalyDetectionUseCase_Factory(repositoryProvider, getDailyExpenseTrendUseCaseProvider, getTopCategoriesByExpenseUseCaseProvider);
  }

  public static AnomalyDetectionUseCase newInstance(TransactionRepository repository,
      GetDailyExpenseTrendUseCase getDailyExpenseTrendUseCase,
      GetTopCategoriesByExpenseUseCase getTopCategoriesByExpenseUseCase) {
    return new AnomalyDetectionUseCase(repository, getDailyExpenseTrendUseCase, getTopCategoriesByExpenseUseCase);
  }
}
