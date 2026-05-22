package com.nyatetduwit.presentation.budget;

import com.nyatetduwit.domain.repository.BudgetRepository;
import com.nyatetduwit.domain.usecase.budget.AddBudgetUseCase;
import com.nyatetduwit.domain.usecase.budget.DeactivateBudgetUseCase;
import com.nyatetduwit.domain.usecase.budget.DeleteBudgetUseCase;
import com.nyatetduwit.domain.usecase.budget.GetBudgetsUseCase;
import com.nyatetduwit.domain.usecase.budget.UpdateBudgetUseCase;
import com.nyatetduwit.domain.usecase.category.GetCategoriesUseCase;
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
public final class BudgetViewModel_Factory implements Factory<BudgetViewModel> {
  private final Provider<GetBudgetsUseCase> getBudgetsUseCaseProvider;

  private final Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider;

  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private final Provider<AddBudgetUseCase> addBudgetUseCaseProvider;

  private final Provider<UpdateBudgetUseCase> updateBudgetUseCaseProvider;

  private final Provider<DeleteBudgetUseCase> deleteBudgetUseCaseProvider;

  private final Provider<DeactivateBudgetUseCase> deactivateBudgetUseCaseProvider;

  public BudgetViewModel_Factory(Provider<GetBudgetsUseCase> getBudgetsUseCaseProvider,
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<AddBudgetUseCase> addBudgetUseCaseProvider,
      Provider<UpdateBudgetUseCase> updateBudgetUseCaseProvider,
      Provider<DeleteBudgetUseCase> deleteBudgetUseCaseProvider,
      Provider<DeactivateBudgetUseCase> deactivateBudgetUseCaseProvider) {
    this.getBudgetsUseCaseProvider = getBudgetsUseCaseProvider;
    this.getCategoriesUseCaseProvider = getCategoriesUseCaseProvider;
    this.budgetRepositoryProvider = budgetRepositoryProvider;
    this.addBudgetUseCaseProvider = addBudgetUseCaseProvider;
    this.updateBudgetUseCaseProvider = updateBudgetUseCaseProvider;
    this.deleteBudgetUseCaseProvider = deleteBudgetUseCaseProvider;
    this.deactivateBudgetUseCaseProvider = deactivateBudgetUseCaseProvider;
  }

  @Override
  public BudgetViewModel get() {
    return newInstance(getBudgetsUseCaseProvider.get(), getCategoriesUseCaseProvider.get(), budgetRepositoryProvider.get(), addBudgetUseCaseProvider.get(), updateBudgetUseCaseProvider.get(), deleteBudgetUseCaseProvider.get(), deactivateBudgetUseCaseProvider.get());
  }

  public static BudgetViewModel_Factory create(
      Provider<GetBudgetsUseCase> getBudgetsUseCaseProvider,
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<AddBudgetUseCase> addBudgetUseCaseProvider,
      Provider<UpdateBudgetUseCase> updateBudgetUseCaseProvider,
      Provider<DeleteBudgetUseCase> deleteBudgetUseCaseProvider,
      Provider<DeactivateBudgetUseCase> deactivateBudgetUseCaseProvider) {
    return new BudgetViewModel_Factory(getBudgetsUseCaseProvider, getCategoriesUseCaseProvider, budgetRepositoryProvider, addBudgetUseCaseProvider, updateBudgetUseCaseProvider, deleteBudgetUseCaseProvider, deactivateBudgetUseCaseProvider);
  }

  public static BudgetViewModel newInstance(GetBudgetsUseCase getBudgetsUseCase,
      GetCategoriesUseCase getCategoriesUseCase, BudgetRepository budgetRepository,
      AddBudgetUseCase addBudgetUseCase, UpdateBudgetUseCase updateBudgetUseCase,
      DeleteBudgetUseCase deleteBudgetUseCase, DeactivateBudgetUseCase deactivateBudgetUseCase) {
    return new BudgetViewModel(getBudgetsUseCase, getCategoriesUseCase, budgetRepository, addBudgetUseCase, updateBudgetUseCase, deleteBudgetUseCase, deactivateBudgetUseCase);
  }
}
